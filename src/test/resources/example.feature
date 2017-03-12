Feature: 登录

  Scenario: 输入账号密码,登录成功
    Given 输入账号user
    And 输入密码pwd
    When 点击登录按钮
    Then 显示登录成功