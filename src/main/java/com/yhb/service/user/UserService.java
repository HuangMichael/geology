package com.yhb.service.user;

import com.yhb.dao.location.LocationRepository;
import com.yhb.dao.person.GenderRepository;
import com.yhb.dao.person.PersonRepository;
import com.yhb.dao.role.RoleRepository;
import com.yhb.dao.user.UserRepository;
import com.yhb.domain.area.AreaMedia;
import com.yhb.domain.location.Location;
import com.yhb.domain.person.Person;
import com.yhb.domain.user.User;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.*;
import com.yhb.utils.ftp.FTPUploader;
import com.yhb.utils.ftp.FTPconfig;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.Mercator;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 用户信息业务类
 */
@Service
public class UserService extends BaseService {


    @Autowired
    GenderRepository genderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * @param user
     * @return 保存用户信息，保存之前进行验证
     */
    public User save(User user) {
        try {
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(Md5Utils.getEncryptedPwd("123456"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }
        return userRepository.save(user);
    }

    /**
     * @param id
     * @param userName
     * @param password
     * @param status
     * @return 保存用户信息
     */
    public User save(Long id, String userName, String personName, String birthDate, Long gender, String department, String password, String status) {
        try {
            User user = new User();
            user.setId(id);
            //id为空则为新增用户，id不为空则为编辑用户
            if (id == null) {
                user.setPassword(Md5Utils.md5("123456"));
                user.setLocation(null);
            } else {
                User oldUser = userRepository.findOne(id);
                user.setPassword(oldUser.getPassword());
                user.setLocation(oldUser.getLocation());
            }
            user.setUserName(userName);
            user.setPersonName(personName);
            user.setGender(genderRepository.findOne(gender));
            user.setBirthDate(DateUtils.convertStr2Date(birthDate, "yyyy-MM-dd"));
            user.setDepartment(department);
            user.setStatus(status);
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param userList 批量删除
     */
    public void deleteInBatch(List userList) {
        userRepository.deleteInBatch(userList);
    }


    /**
     * @param id 根据ID删除用户信息信息，如果有关联信息不能删除
     */
    public ReturnObject delete(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的用户不存在,请确认！");
        }

        //判断user 是否在role的关联关系中
        try {
            userRepository.delete(user);
            //再查询一次查看是否删除
            User user1 = userRepository.findOne(id);
            return commonDataService.getReturnType(user1 == null, "用户信息删除成功！", "用户信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "用户信息删除成功！", "用户信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public User findById(Long id) {
        return userRepository.findOne(id);

    }

    /**
     * @param userName
     * @return 根据用户名称查询
     */
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * @param id
     * @param userName
     * @return 编辑用户信息时，根据前端传过来的用户输入的新用户名称和id查询是否与其他的用户重名。如果重复，返回一个User；不重复返回null
     */
    public User findUserDuplicateByUserNameAndId(Long id, String userName) {
        //查询不是该id的其他用户id列表
        List<Long> otherUserIds = selectOtherIds(id);
        return userRepository.findByUserNameAndIdIn(userName, otherUserIds);
    }

    /**
     * @return 查询所有的用户信息信息
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }


    /**
     * @return 返回所有的id列表
     */
    public List<Long> selectAllIds() {
        return userRepository.selectAllIds();
    }

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    public List<Long> selectOtherIds(Long id) {
        return userRepository.selectOtherIds(id);
    }

    /**
     * @param userName 用户名
     * @param password 密码密文
     * @param status   用户状态
     * @return
     */
    public ReturnObject findByUserNameAndPasswordAndStatus(HttpServletRequest request, String userName, String password, String status) {
        User userFound = userRepository.findByUserName(userName);
        String failMessage = "";
        Boolean result = false;
        User user;
        if (userFound == null) {
            failMessage = "用户名不存在，请联系管理员!";
        } else {
            user = userFound;
            if (user.getPassword() == null || user.getStatus() == null) {
                failMessage = "用户密码或状态为空,请重新输入";
            } else if (user.getPassword() != null && !user.getPassword().equals(password)) {
                failMessage = "用户密码有误,请重新输入";
            } else if (user.getStatus() != null && !user.getStatus().equals(status)) {
                failMessage = "用户信息为锁定状态,请联系管理员!";
            } else {
                result = true;
                //将用户人员信息放入session.
                HttpSession session = request.getSession();
                Location myLocation = user.getLocation();
                String locCode = myLocation.getLocCode();
                session.setAttribute("user", user);
                session.setAttribute("myLocation", myLocation);
                session.setAttribute("locCode", locCode);
            }
        }
        return commonDataService.getReturnType(result, "用户登录成功", failMessage);
    }


    /**
     * @param userName 用户名
     * @return 根据用户名查询人员信息
     */
    public Person findPersonByUserName(String userName) {
        User userFound = userRepository.findByUserName(userName);
        Person person = null;
        return person;
    }


    /**
     * @param locationId
     * @return 查询位置下对应的用户信息
     */
    public List<User> findByLocation(Long locationId) {
        return userRepository.findByLocationIdOrderById(locationId);
    }


    /**
     * @param locationId 对位置locationId对人员进行授权
     * @param userIds
     * @return
     */
    public ReturnObject grantDataAuth(Long locationId, String userIds) {
        Location location = locationRepository.findOne(locationId);
        String userIdArray[] = userIds.split(",");
        Long userId = null;
        User user = null;
        List<User> users = new ArrayList<User>();
        for (String str : userIdArray) {
            userId = Long.parseLong(str);
            user = userRepository.findOne(userId);
            user.setLocation(location);
            userRepository.save(user);
            users.add(user);
        }
        String msg = location.getLocName() + users.size() + "个用户";
        return commonDataService.getReturnType(!users.isEmpty(), msg + "数据授权成功", "数据授权失败，请重试");
    }


    /**
     * @param locationId 位置id
     * @return 查询不在该位置下的用户信息
     */
    public List<User> findByLocationIsNot(Long locationId) {
        return userRepository.findByLocationIsNotOrLocationIsNull(locationId);
    }


    /**
     * @param userName
     * @param password
     * @return 根据用户名和密码验证用户输入的密码password是否正确
     */
    public ReturnObject verifyPassword(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty()) {
            return commonDataService.getReturnType(false, "", "验证失败！");
        }
        boolean result = false;//验证结果，初始化为false
        try {
            //后台对前台传过来的密码进行MD5加密
            String pwEncrypted = Md5Utils.md5(password);
            User user = userRepository.findByUserName(userName);
            String pwInDb = user.getPassword();
            result = pwEncrypted.equals(pwInDb) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return commonDataService.getReturnType(result, "验证成功,密码正确!", "验证失败，密码不正确！");
    }

    /**
     * 根据用户名和新密码修改密码
     *
     * @param userName
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    public ReturnObject modifyPassword(String userName, String oldPassword, String newPassword) {
        if (userName.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
            return commonDataService.getReturnType(false, "", "修改失败，用户名、旧密码、新密码均不能为空！");
        }
        ReturnObject returnObject = verifyPassword(userName, oldPassword);
        if (!returnObject.getResult()) {
            return commonDataService.getReturnType(false, "", "验证失败，旧密码不正确！");
        }
        if (oldPassword.equals(newPassword)) {
            return commonDataService.getReturnType(false, "", "验证失败，新旧密码相同！");
        }
        String newPasswordEncrypted = Md5Utils.md5(newPassword);
        User user = userRepository.findByUserName(userName);
        user.setPassword(newPasswordEncrypted);
        User user1 = userRepository.save(user);
        return commonDataService.getReturnType(user1 != null, "密码修改成功！", "密码修改失败！");
    }

    /**
     * @param userId
     * @return 取消用户数据授权
     */
    public Boolean revokeLoc(Long userId) {
        User user = userRepository.findOne(userId);
        user.setLocation(null);
        userRepository.save(user);
        return user.getLocation() == null;
    }

    /**
     * @param locationId
     * @return 根据位置id和用户名查询位置下对应的用户信息
     */
    public List<User> findByLocationIdAndUserName(Long locationId, String userName) {
        return userRepository.findByLocationIdAndUserNameContainsOrderById(locationId, userName);
    }

    /**
     * @param locId
     * @param current
     * @param rowCount
     * @param searchPhrase
     * @return 查询该位置下的用户列表，分页显示
     */
    public MyPage getLocationUsersMyPage(Long locId, int current, Long rowCount, String searchPhrase) {
        //复合查询条件，用户名
        String array[] = super.assembleSearchArray(searchPhrase, 1);
        List<User> usersList = findByLocationIdAndUserName(locId, array[0]);//获取属于该位置的且包含用户名的用户列表
        return new PageUtils().getUserListMyPage(usersList, current, rowCount);
    }

    /**
     * @param locId
     * @param current
     * @param rowCount
     * @param searchPhrase
     * @return 查询该位置下未进行授权的用户信息列表，分页显示
     */
    public MyPage popUsersMyPage(Long locId, int current, Long rowCount, String searchPhrase) {
        List<User> usersList = findByLocationIsNot(locId);//获取不属于该位置的用户列表
        return new PageUtils().getUserListMyPage(usersList, current, rowCount);
    }


    /**
     * @param locId 用户位置
     * @return 根据用户的位置获取地图中心点
     */
    public Mercator getUserPositionCenter(Long locId) {
        String sql = "select st_x (st_centroid(shape)) AS x, st_y (st_centroid(shape)) AS y from t_locations   where id = " + locId;
        log.info(sql);
        List<Map> mapList = (List) jdbcTemplate.queryForList(sql);
        Double x = null, y = null;
        for (Map map : mapList) {
            log.info(map.keySet());
            x = Double.parseDouble(map.get("x").toString());
            y = Double.parseDouble(map.get("y").toString());
        }
        Mercator mercator = new Mercator();
        mercator.setX(x);
        mercator.setY(y);
        return mercator;
    }


    /**
     * @param file
     * @param request
     * @param session
     * @param userName
     * @return 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     */
    @Transactional
    public User uploadFileAndSavePath(MultipartFile file, HttpServletRequest request, HttpSession session, String userName) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//精确到时分秒
        Date current = new Date();//获取当前时间
        String timeStr = sdf.format(current);
        String dirStr = "mediaDocs/user/" + timeStr;//项目多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        if (!UploadUtil.createDirectory(realDir)) {//目录创建失败则返回null，目录存在或者创建成功就继续执行
            return null;
        }
        String fileName = file.getOriginalFilename().replace(" ", "");//文件名，去掉文件名中的空格
        String filePath = realDir + "\\" + fileName;//绝对文件路径
        Long mediaTypeId = 1L;//多媒体类型默认为文档
        Boolean result = UploadUtil.uploadFile(file, filePath);//上传文件到Tomcat，作为临时文件
        log.info("上传到web服务器--------------" + result);
        //仅对图片进行压缩和显示缩略图
        return saveUploadLog(fileName, file, dirStr, mediaTypeId, session, userName, current);

    }


    /**
     * @param fileName
     * @param file
     * @param dirStr
     * @param mediaTypeId
     * @param session
     * @param userName
     * @param current
     * @return 保存上传日志
     */
    public User saveUploadLog(String fileName, MultipartFile file, String dirStr, Long mediaTypeId, HttpSession session, String userName, Date current) {
        //如果上传到ftp成功，就保存多媒体信息到数据库
//        AreaMedia areaMedia = new AreaMedia();
//        areaMedia.setFileName(fileName);
//        areaMedia.setFileSize(file.getSize());
//        String nginxUrl = ftPconfig.getFTPUrl() + "/" + dirStr + "/" + fileName;
//        areaMedia.setFileRelativeUrl(nginxUrl);
//        areaMedia.setMediaTypes(mediaTypeRepository.findOne(mediaTypeId));
//        areaMedia.setMediaTree(mediaTreeRepository.findOne(treeNodeId));
//        areaMedia.setThumbNailUrl((mediaTypeId == 2L) ? StringUtils.renameNgnixFile(nginxUrl) : nginxUrl);
//        areaMedia.setStatus("0");//默认为未审核状态，设置为1则为已审核状态
//        areaMedia.setAuthKey(DataFilterUtils.getAuthKey(session));
//        areaMedia.setUser(userRepository.findByUserName(userName));//设置上传的用户
//        areaMedia.setUploadDate(current);//设置上传时间
//        areaMedia.setArea(areaRepository.getOne(areaId));
//        return areaMediaRepository.save(areaMedia);

        return null;

    }


}
