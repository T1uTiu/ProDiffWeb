package com.tiutiu.prodiff.mapper;

import com.tiutiu.prodiff.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {
    UserAccount getUserAccountByEmail(String email);

    void insertUserAccount(UserAccount userAccount);
}
