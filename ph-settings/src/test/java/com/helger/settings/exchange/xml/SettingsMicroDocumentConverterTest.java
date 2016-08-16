/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
package com.helger.settings.exchange.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.time.Period;

import org.junit.Test;

import com.helger.commons.charset.CCharset;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.string.StringParser;
import com.helger.settings.IMutableSettings;
import com.helger.settings.ISettings;
import com.helger.settings.Settings;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.convert.MicroTypeConverter;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Test class for class {@link SettingsMicroDocumentConverter}.
 *
 * @author Philip Helger
 */
public final class SettingsMicroDocumentConverterTest
{
  @Test
  public void testConversionWithTypes () throws UnsupportedEncodingException
  {
    final Settings aSrc = new Settings ("myName");
    aSrc.setValue ("field1a", BigInteger.valueOf (1234));
    aSrc.setValue ("field1b", BigInteger.valueOf (-23423424));
    aSrc.setValue ("field2a", BigDecimal.valueOf (12.34));
    aSrc.setValue ("field2b", BigDecimal.valueOf (-2342.334599424));
    aSrc.setValue ("field3a", "My wonderbra string\n(incl newline)");
    aSrc.setValue ("field3b", "");
    aSrc.setValue ("field9a", Boolean.TRUE);
    aSrc.setValue ("field9b", StringParser.parseByteObj ("5"));
    aSrc.setValue ("field9c", Character.valueOf ('ä'));
    aSrc.setValue ("fieldxa", PDTFactory.getCurrentLocalDate ());
    aSrc.setValue ("fieldxb", PDTFactory.getCurrentLocalTime ());
    aSrc.setValue ("fieldxc", PDTFactory.getCurrentLocalDateTime ());
    aSrc.setValue ("fieldxd", PDTFactory.getCurrentZonedDateTime ());
    aSrc.setValue ("fieldxe", Duration.ofHours (5));
    aSrc.setValue ("fieldxf", Period.ofDays (3));
    aSrc.setValue ("fieldxg", "Any byte ärräy".getBytes (CCharset.CHARSET_UTF_8));

    final Settings aNestedSettings = new Settings ("nestedSettings");
    aNestedSettings.setValue ("a", "b");
    aNestedSettings.setValue ("c", "d");
    aNestedSettings.setValue ("e", Clock.systemDefaultZone ().millis ());
    aSrc.setValue ("fieldxh", aNestedSettings);

    // To XML
    final IMicroElement eSrcElement = MicroTypeConverter.convertToMicroElement (aSrc, "root");
    assertNotNull (eSrcElement);
    if (false)
      System.out.println (MicroWriter.getXMLString (eSrcElement));

    // From XML
    final ISettings aDst = MicroTypeConverter.convertToNative (eSrcElement, Settings.class);
    assertNotNull (aDst);
    assertEquals (aSrc, aDst);

    // Compare list
    assertEquals (BigInteger.valueOf (1234), aDst.getValue ("field1a"));

    final IMutableSettings aDst2 = new Settings (aDst.getName ());
    aDst2.setValues (aDst);
    assertEquals (aDst, aDst2);
    assertTrue (aDst2.setValue ("field3b", "doch was").isChanged ());
    assertFalse (aDst.equals (aDst2));
  }
}