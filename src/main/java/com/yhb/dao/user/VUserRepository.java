package com.yhb.dao.user;

import com.yhb.domain.user.VUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
public interface VUserRepository extends PagingAndSortingRepository<VUser, Long> {

    /**
     * @param userName 根据用户姓名查询
     * @param selectedIds
     * @return
     */
    List<VUser> findByUserNameContainsAndIdIn(String userName, List<Long> selectedIds);

    /**
     * @param userName 据用户姓名查询
     * @return
     */
    Page<VUser> findByUserNameContains(String userName ,Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VUser a ")
    List<Long> selectAllIds();

}
