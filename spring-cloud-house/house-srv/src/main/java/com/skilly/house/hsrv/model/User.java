package com.mooc.house.hsrv.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class User {
  
  private Long id;
  private String  name;
  private String  phone;
  private String  email;
  private String  aboutme;
  private String  passwd;
  private String  confirmPasswd;
  private Integer type;
  private Date    createTime;
  private Integer enable;
  
  private String  avatar;
  
  private MultipartFile avatarFile;
  
  private String newPassword;
  
  private String key;
  
  private Long  agencyId;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAboutme() {
    return aboutme;
  }
  public void setAboutme(String aboutme) {
    this.aboutme = aboutme;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
 
  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  public String getConfirmPasswd() {
    return confirmPasswd;
  }
  public void setConfirmPasswd(String confirmPasswd) {
    this.confirmPasswd = confirmPasswd;
  }
  
  
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public Integer getEnable() {
    return enable;
  }
  public void setEnable(Integer enable) {
    this.enable = enable;
  }
  
  public MultipartFile getAvatarFile() {
    return avatarFile;
  }
  public void setAvatarFile(MultipartFile avatarFile) {
    this.avatarFile = avatarFile;
  }
  public String getNewPassword() {
    return newPassword;
  }
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
  public Long getAgencyId() {
    return agencyId;
  }
  public void setAgencyId(Long agencyId) {
    this.agencyId = agencyId;
  }

  

}
