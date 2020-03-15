/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.config.source.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.config.source.EConfigSourceType;

/**
 * Test class for class {@link ConfigurationSourcePropertiesFile}.
 *
 * @author Philip Helger
 */
public final class ConfigurationSourcePropertiesFileTest
{
  private static final File f = new File ("src/test/resources/file/test.properties");

  @Test
  public void testBasic ()
  {
    final ConfigurationSourcePropertiesFile c = new ConfigurationSourcePropertiesFile (f);
    assertSame (EConfigSourceType.FILE, c.getSourceType ());
    assertEquals (EConfigSourceType.FILE.getDefaultPriority (), c.getPriority ());
    assertTrue (c.isInitializedAndUsable ());
    assertSame (f, c.getFile ());
    assertEquals ("string", c.getConfigurationValue ("element1"));
    assertEquals ("2", c.getConfigurationValue ("element2"));
    assertNull (c.getConfigurationValue ("what a mess"));

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (c, new ConfigurationSourcePropertiesFile (f));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (c,
                                                                           new ConfigurationSourcePropertiesFile (1234,
                                                                                                                  f));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (c,
                                                                           new ConfigurationSourcePropertiesFile (new File ("bla")));
  }

  @Test
  public void testExplicitCharset ()
  {
    final ConfigurationSourcePropertiesFile c = new ConfigurationSourcePropertiesFile (f, StandardCharsets.ISO_8859_1);
    assertSame (EConfigSourceType.FILE, c.getSourceType ());
    assertEquals (EConfigSourceType.FILE.getDefaultPriority (), c.getPriority ());
    assertTrue (c.isInitializedAndUsable ());
    assertEquals ("string", c.getConfigurationValue ("element1"));
    assertEquals ("2", c.getConfigurationValue ("element2"));
  }

  @Test
  public void testDifferentPriority ()
  {
    final ConfigurationSourcePropertiesFile c = new ConfigurationSourcePropertiesFile (2323, f);
    assertSame (EConfigSourceType.FILE, c.getSourceType ());
    assertEquals (2323, c.getPriority ());
    assertTrue (c.isInitializedAndUsable ());
    assertEquals ("string", c.getConfigurationValue ("element1"));
    assertEquals ("2", c.getConfigurationValue ("element2"));
  }

  @Test
  public void testDifferentPriorityAndExplicitCharset ()
  {
    final ConfigurationSourcePropertiesFile c = new ConfigurationSourcePropertiesFile (2323,
                                                                                       f,
                                                                                       StandardCharsets.ISO_8859_1);
    assertSame (EConfigSourceType.FILE, c.getSourceType ());
    assertEquals (2323, c.getPriority ());
    assertTrue (c.isInitializedAndUsable ());
    assertEquals ("string", c.getConfigurationValue ("element1"));
    assertEquals ("2", c.getConfigurationValue ("element2"));
  }

  @Test
  public void testNonExisting ()
  {
    final File f2 = new File ("bla");
    final ConfigurationSourcePropertiesFile c = new ConfigurationSourcePropertiesFile (f2);
    assertSame (EConfigSourceType.FILE, c.getSourceType ());
    assertEquals (EConfigSourceType.FILE.getDefaultPriority (), c.getPriority ());
    assertFalse (c.isInitializedAndUsable ());
    assertSame (f2, c.getFile ());
    assertNull (c.getConfigurationValue ("element1"));
  }
}