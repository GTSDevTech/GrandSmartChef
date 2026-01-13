package com.grandchefsupreme.suites;


import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.grandchefsupreme.junit_tests")
@DisplayName("Suite de Login")
@IncludeTags("Login")
@ExcludeTags("Logout")
public class SuiteLogin {




}
