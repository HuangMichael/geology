package com.yhb.utils;


import com.yhb.domain.user.User;
import com.yhb.domain.user.UserForAddUsers;
import com.yhb.utils.search.Searchable;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import com.yhb.utils.search.ViewSortedSearchable;
import com.yhb.vo.app.MyPage;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象转换类
 *
 * @author huangbin
 * @create 2017年5月9日10:20:20
 **/
@Data
public class PageUtils {


    public PageUtils() {

    }

    /**
     * @param searchable   实现多条件查询的接口的业务类
     * @param searchPhrase 查询字符串
     * @param paramSize    查询参数个数
     * @param current      当前页
     * @param rowCount     每页条数
     * @return
     */
    public MyPage searchByService(Searchable searchable, String searchPhrase, int paramSize, int current, Long rowCount) {
        Page page = searchable.findByConditions(searchPhrase, paramSize, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }

    /**
     * @param sortedSearchable 实现排序多条件查询的接口的业务类
     * @param searchPhrase     查询字符串
     * @param paramSize        查询参数个数
     * @param current          当前页
     * @param rowCount         每页条数
     * @param pageable         分页参数
     * @return
     */
    public MyPage searchBySortService(SortedSearchable sortedSearchable, String searchPhrase, int paramSize, int current, Long rowCount, Pageable pageable) {
        Page page = sortedSearchable.findByConditions(searchPhrase, paramSize, pageable);
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }

    /**
     * @param sortedSearchableWithSelectedIds 实现 排序多条件查询 且查询用户选择的记录 的接口的业务类
     * @param searchPhrase                    查询字符串
     * @param paramSize                       查询参数个数
     * @param current                         当前页
     * @param rowCount                        每页条数
     * @param pageable                        分页参数
     * @return
     */
    public MyPage searchBySortServiceWithSelectedIds(SortedSearchableWithSelectedIds sortedSearchableWithSelectedIds, String searchPhrase, int paramSize, int current, Long rowCount, Pageable pageable) {
        Page page = sortedSearchableWithSelectedIds.findByConditions(searchPhrase, paramSize, pageable);
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }

    /**
     * @param viewSortedSearchable 实现排序多条件查询的接口的业务类
     * @param searchPhrase         查询字符串
     * @param paramSize            查询参数个数
     * @param current              当前页
     * @param rowCount             每页条数
     * @param pageable             分页参数
     * @return
     */
    public MyPage searchVBySortService(ViewSortedSearchable viewSortedSearchable, String searchPhrase, int paramSize, int current, Long rowCount, Pageable pageable) {
        Page page = viewSortedSearchable.findVByConditions(searchPhrase, paramSize, pageable);
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }

    /**
     * @param usersList
     * @param current
     * @param rowCount
     * @return 根据 当前页码索引current 以及 每页显示的行数rowCount 获取出当前页相应的用户数据记录
     */
    public MyPage getUserListMyPage(List<User> usersList, int current, Long rowCount) {
        MyPage myPage = new MyPage();//初始化返回结果
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        if (usersList == null || usersList.size() == 0) {
            myPage.setRows(usersList);
            myPage.setTotal(0);
            return myPage;
        }
        Long totalCounts = (long) usersList.size();//总的用户个数
        List<UserForAddUsers> addUsersList = new ArrayList<UserForAddUsers>();
        for (int i = 0; i < totalCounts; i++) {
            UserForAddUsers userForAddUsers = new UserForAddUsers();
            userForAddUsers.setId(usersList.get(i).getId());
            userForAddUsers.setUserName(usersList.get(i).getUserName());
            userForAddUsers.setDepartment(usersList.get(i).getDepartment());
            User u = usersList.get(i);
            String pName = null;//人员名称
            if (u != null) {
                pName = u.getPersonName();

            }
            userForAddUsers.setPersonName(pName);
            addUsersList.add(userForAddUsers);
        }

        //根据 当前页码索引current 以及 每页显示的行数rowCount 获取出当前页相应的数据记录
        List<UserForAddUsers> finalList = new ArrayList<UserForAddUsers>();
        Long residualCounts = totalCounts - current * rowCount;
        if (residualCounts >= 0) {
            for (long i = (current - 1) * rowCount; i < current * rowCount; i++) {
                finalList.add(addUsersList.get((int) i));
            }
        } else {
            for (long i = (current - 1) * rowCount; i < totalCounts; i++) {
                finalList.add(addUsersList.get((int) i));
            }
        }
        myPage.setRows(finalList);
        myPage.setTotal(totalCounts);
        return myPage;
    }
}
