package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

  @Id
  private Long id;

  @Column(name = "name")
  private String username;

  private Integer age;

  @Enumerated(EnumType.STRING) // EnumType.ORDINAL 사용 금지
  private RoleType roleType;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  private LocalDate createDate2; // 자바 8 이후에는 이렇게 사용

  private LocalDateTime createDate3;

  @Lob
  private String description;

  @Transient
  private int temp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public LocalDate getCreateDate2() {
    return createDate2;
  }

  public void setCreateDate2(LocalDate createDate2) {
    this.createDate2 = createDate2;
  }

  public LocalDateTime getCreateDate3() {
    return createDate3;
  }

  public void setCreateDate3(LocalDateTime createDate3) {
    this.createDate3 = createDate3;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getTemp() {
    return temp;
  }

  public void setTemp(int temp) {
    this.temp = temp;
  }
}
