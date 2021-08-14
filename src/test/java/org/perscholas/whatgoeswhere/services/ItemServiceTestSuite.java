package org.perscholas.whatgoeswhere.services;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ItemServiceTest.class, ItemParameterizedTest.class})
class ItemServiceTestSuite {

}
