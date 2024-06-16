package com.tiutiu.prodiff.service;

import com.tiutiu.prodiff.dto.UserAccountLoginDTO;
import com.tiutiu.prodiff.dto.UserAccountRegisterDTO;
import com.tiutiu.prodiff.entity.UserAccount;

public interface UserAccountService {
    UserAccount login(UserAccountLoginDTO userAccountLoginDTO);
    String buildToken(UserAccount userAccount);
    void sendEmailVerificationCode(String email, String address);
    String register(UserAccountRegisterDTO userAccountRegisterDTO);
    void logout();
    UserAccount getUserAccount(String email);
}
