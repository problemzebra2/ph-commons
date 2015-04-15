/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.commons;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotations.DevelopersNote;
import com.helger.commons.annotations.PresentForCodeCoverage;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.priviledged.AccessControllerHelper;
import com.helger.commons.priviledged.PrivilegedActionSystemClearProperty;
import com.helger.commons.priviledged.PrivilegedActionSystemGetProperties;
import com.helger.commons.priviledged.PrivilegedActionSystemGetProperty;
import com.helger.commons.priviledged.PrivilegedActionSystemSetProperty;

/**
 * This class wraps all the Java system properties like version number etc.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class SystemProperties
{
  public static final String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
  public static final String SYSTEM_PROPERTY_JAVA_CLASS_PATH = "java.class.path";
  public static final String SYSTEM_PROPERTY_JAVA_CLASS_VERSION = "java.class.version";
  public static final String SYSTEM_PROPERTY_JAVA_LIBRARY_PATH = "java.library.path";
  public static final String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
  public static final String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
  public static final String SYSTEM_PROPERTY_JAVA_SPECIFICATION_URL = "java.specification.url";
  public static final String SYSTEM_PROPERTY_JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
  public static final String SYSTEM_PROPERTY_JAVA_SPECIFICATION_VERSION = "java.specification.version";
  public static final String SYSTEM_PROPERTY_JAVA_VENDOR = "java.vendor";
  public static final String SYSTEM_PROPERTY_JAVA_VENDOR_URL = "java.vendor.url";
  public static final String SYSTEM_PROPERTY_JAVA_VERSION = "java.version";
  public static final String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
  public static final String SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_URL = "java.vm.specification.url";
  public static final String SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";
  public static final String SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
  public static final String SYSTEM_PROPERTY_JAVA_VM_URL = "java.vm.url";
  public static final String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
  public static final String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
  public static final String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
  public static final String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
  public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
  public static final String SYSTEM_PROPERTY_OS_VERSION = "os.version";
  public static final String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";
  public static final String SYSTEM_PROPERTY_USER_DIR = "user.dir";
  public static final String SYSTEM_PROPERTY_USER_HOME = "user.home";
  public static final String SYSTEM_PROPERTY_USER_NAME = "user.name";

  public static final String SYSTEM_PROPERTY_JDX_XML_ENTITY_EXPANSION_LIMIT = "jdx.xml.entityExpansionLimit";
  public static final String SYSTEM_PROPERTY_ENTITY_EXPANSION_LIMIT = "entityExpansionLimit";
  public static final String SYSTEM_PROPERTY_JDX_XML_ELEMENT_ATTRIBUTE_LIMIT = "jdx.xml.elementAttributeLimit";
  public static final String SYSTEM_PROPERTY_ELEMENT_ATTRIBUTE_LIMIT = "elementAttributeLimit";
  public static final String SYSTEM_PROPERTY_JDX_XML_MAX_OCCUR = "jdx.xml.maxOccur";
  public static final String SYSTEM_PROPERTY_MAX_OCCUR = "maxOccur";
  public static final String SYSTEM_PROPERTY_JDX_XML_TOTAL_ENTITY_SIZE_LIMIT = "jdx.xml.totalEntitySizeLimit";
  public static final String SYSTEM_PROPERTY_JDX_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT = "jdx.xml.maxGeneralEntitySizeLimit";
  public static final String SYSTEM_PROPERTY_JDX_XML_MAX_PARAMETER_ENTITY_SIZE_LIMIT = "jdx.xml.maxParameterEntitySizeLimit";

  private static final Logger s_aLogger = LoggerFactory.getLogger (SystemProperties.class);
  private static final Set <String> s_aWarnedPropertyNames = Collections.synchronizedSet (new HashSet <String> ());

  @PresentForCodeCoverage
  private static final SystemProperties s_aInstance = new SystemProperties ();

  private SystemProperties ()
  {}

  @Nullable
  public static String getPropertyValueOrNull (@Nullable final String sKey)
  {
    return sKey == null ? null : AccessControllerHelper.call (new PrivilegedActionSystemGetProperty (sKey));
  }

  @Nullable
  public static String getPropertyValue (@Nullable final String sKey)
  {
    String ret = null;
    if (sKey != null)
    {
      ret = getPropertyValueOrNull (sKey);
      if (ret == null && s_aWarnedPropertyNames.add (sKey))
      {
        // Warn about each property once
        s_aLogger.warn ("System property '" + sKey + "' is not set!");
      }
    }
    return ret;
  }

  /**
   * Clear the cache with the property names, for which warnings were emitted
   * that keys don't exist.
   */
  public static void clearWarnedPropertyNames ()
  {
    s_aWarnedPropertyNames.clear ();
  }

  /**
   * @return A copy of the set with all property names for which warnings were
   *         emitted.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static Set <String> getAllWarnedPropertyNames ()
  {
    return new HashSet <String> (s_aWarnedPropertyNames);
  }

  /**
   * Set a system property value under consideration of an eventually present
   * {@link SecurityManager}.
   *
   * @param sKey
   *        The key of the system property. May not be <code>null</code>.
   * @param sValue
   *        The value of the system property. If the value is <code>null</code>
   *        the property is removed.
   */
  public static void setPropertyValue (@Nonnull final String sKey, @Nullable final String sValue)
  {
    if (sValue == null)
      removePropertyValue (sKey);
    else
      AccessControllerHelper.run (new PrivilegedActionSystemSetProperty (sKey, sValue));
  }

  /**
   * Remove a system property value under consideration of an eventually present
   * {@link SecurityManager}.
   *
   * @param sKey
   *        The key of the system property to be removed. May not be
   *        <code>null</code>.
   * @return the previous string value of the system property, or
   *         <code>null</code> if there was no property with that key.
   */
  @Nullable
  public static String removePropertyValue (@Nonnull final String sKey)
  {
    return AccessControllerHelper.call (new PrivilegedActionSystemClearProperty (sKey));
  }

  @Nullable
  public static String getJavaVersion ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VERSION);
  }

  @Nullable
  public static String getJavaVendor ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VENDOR);
  }

  @Nullable
  public static String getJavaVendorURL ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VENDOR_URL);
  }

  @Nullable
  public static String getJavaHome ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_HOME);
  }

  @Nullable
  public static String getJavaClassVersion ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_CLASS_VERSION);
  }

  @Nullable
  public static String getJavaClassPath ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_CLASS_PATH);
  }

  @Nullable
  public static String getJavaLibraryPath ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_LIBRARY_PATH);
  }

  @Nullable
  public static String getOsName ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_OS_NAME);
  }

  @Nullable
  public static String getOsArch ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_OS_ARCH);
  }

  @Nullable
  public static String getOsVersion ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_OS_VERSION);
  }

  @Nullable
  public static String getFileSeparator ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_FILE_SEPARATOR);
  }

  @Nullable
  public static String getPathSeparator ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_PATH_SEPARATOR);
  }

  @Nullable
  public static String getLineSeparator ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_LINE_SEPARATOR);
  }

  @Nullable
  public static String getUserName ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_USER_NAME);
  }

  @Nullable
  public static String getUserHome ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_USER_HOME);
  }

  @Nullable
  public static String getUserDir ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_USER_DIR);
  }

  @Nullable
  public static String getJavaVmName ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_NAME);
  }

  @Nullable
  public static String getJavaVmSpecificationVersion ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_VERSION);
  }

  @Nullable
  public static String getJavaVmSpecificationVendor ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_VENDOR);
  }

  @Nullable
  public static String getJavaVmSpecificationUrl ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_SPECIFICATION_URL);
  }

  @Nullable
  public static String getJavaVmVersion ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_VERSION);
  }

  @Nullable
  public static String getJavaVmVendor ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_VENDOR);
  }

  @Nullable
  public static String getJavaVmUrl ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_VM_URL);
  }

  @Nullable
  public static String getJavaSpecificationVersion ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_SPECIFICATION_VERSION);
  }

  @Nullable
  public static String getJavaSpecificationVendor ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_SPECIFICATION_VENDOR);
  }

  @Nullable
  public static String getJavaSpecificationUrl ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_SPECIFICATION_URL);
  }

  @DevelopersNote ("This property is not part of the language but part of the Sun SDK")
  @Nullable
  public static String getTmpDir ()
  {
    return getPropertyValue (SYSTEM_PROPERTY_JAVA_IO_TMPDIR);
  }

  /**
   * @return A set with all defined property names. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static Set <String> getAllPropertyNames ()
  {
    return new HashSet <String> (getAllProperties ().keySet ());
  }

  /**
   * @return A map with all system properties where the key is the system
   *         property name and the value is the system property value.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static Map <String, String> getAllProperties ()
  {
    final Map <String, String> ret = new HashMap <String, String> ();
    final Properties aProperties = AccessControllerHelper.call (new PrivilegedActionSystemGetProperties ());
    if (aProperties != null)
      for (final Map.Entry <Object, Object> aEntry : aProperties.entrySet ())
      {
        final String sKey = (String) aEntry.getKey ();
        ret.put (sKey, (String) aEntry.getValue ());
      }
    return ret;
  }

  /**
   * Check if a system property with the given name exists.
   *
   * @param sPropertyName
   *        The name of the property.
   * @return <code>true</code> if such a system property is present,
   *         <code>false</code> otherwise
   */
  public static boolean containsPropertyName (final String sPropertyName)
  {
    return getAllProperties ().containsKey (sPropertyName);
  }

  /**
   * Limit the number of entity expansions.
   *
   * @param nEntityExpansionLimit
   *        A positive integer. Values &ge; 0 are treated as no limit.
   */
  public static void setXMLEntityExpansionLimit (final int nEntityExpansionLimit)
  {
    setPropertyValue (SYSTEM_PROPERTY_ENTITY_EXPANSION_LIMIT, Integer.toString (nEntityExpansionLimit));
    setPropertyValue (SYSTEM_PROPERTY_JDX_XML_ENTITY_EXPANSION_LIMIT, Integer.toString (nEntityExpansionLimit));
  }

  public static int getXMLEntityExpansionLimit ()
  {
    // Default value depends.
    // JDK 1.6: 100.000
    // JDK 1.7+: 64.0000
    String sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_JDX_XML_ENTITY_EXPANSION_LIMIT);
    if (sPropertyValue == null)
      sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_ENTITY_EXPANSION_LIMIT);
    if (sPropertyValue == null)
      return 64000;
    return Integer.parseInt (sPropertyValue);
  }

  /**
   * Limit the number of attributes an element can have
   *
   * @param nElementAttributeLimit
   *        A positive integer. Values &ge; 0 are treated as no limit.
   */
  public static void setXMLElementAttributeLimit (final int nElementAttributeLimit)
  {
    setPropertyValue (SYSTEM_PROPERTY_ELEMENT_ATTRIBUTE_LIMIT, Integer.toString (nElementAttributeLimit));
    setPropertyValue (SYSTEM_PROPERTY_JDX_XML_ELEMENT_ATTRIBUTE_LIMIT, Integer.toString (nElementAttributeLimit));
  }

  public static int getXMLElementAttributeLimit ()
  {
    // Default value depends.
    // JDK 1.7+: 10.0000
    String sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_JDX_XML_ELEMENT_ATTRIBUTE_LIMIT);
    if (sPropertyValue == null)
      sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_ELEMENT_ATTRIBUTE_LIMIT);
    if (sPropertyValue == null)
      return 10000;
    return Integer.parseInt (sPropertyValue);
  }

  /**
   * Limit the number of contentmodel nodes that may be created when building a
   * grammar for a W3C XML Schema that contains maxOccurs attributes with values
   * other than "unbounded".
   *
   * @param nMaxOccur
   *        A positive integer. Values &ge; 0 are treated as no limit.
   */
  public static void setXMLMaxOccur (final int nMaxOccur)
  {
    setPropertyValue (SYSTEM_PROPERTY_MAX_OCCUR, Integer.toString (nMaxOccur));
    setPropertyValue (SYSTEM_PROPERTY_JDX_XML_MAX_OCCUR, Integer.toString (nMaxOccur));
  }

  public static int getXMLMaxOccur ()
  {
    // Default value depends.
    // JDK 1.7+: 5.0000
    String sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_JDX_XML_MAX_OCCUR);
    if (sPropertyValue == null)
      sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_MAX_OCCUR);
    if (sPropertyValue == null)
      return 5000;
    return Integer.parseInt (sPropertyValue);
  }

  /**
   * Limit the total size of all entities that include general and parameter
   * entities. The size is calculated as an aggregation of all entities.<br>
   * This is available since JDK 1.7.0_45/1.8
   *
   * @param nTotalEntitySizeLimit
   *        A positive integer. Values &ge; 0 are treated as no limit.
   */
  public static void setXMLTotalEntitySizeLimit (final int nTotalEntitySizeLimit)
  {
    setPropertyValue (SYSTEM_PROPERTY_JDX_XML_TOTAL_ENTITY_SIZE_LIMIT, Integer.toString (nTotalEntitySizeLimit));
  }

  public static int getXMLTotalEntitySizeLimit ()
  {
    // Default value:
    // JDK 1.7.0_45: 5x10^7
    final String sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_JDX_XML_TOTAL_ENTITY_SIZE_LIMIT);
    if (sPropertyValue == null)
      return 5 * (int) 10e7;
    return Integer.parseInt (sPropertyValue);
  }

  /**
   * Limit the maximum size of any general entities. It is recommended that
   * users set the limit to the smallest possible number so that malformed xml
   * files can be caught quickly.<br>
   * This is available since JDK 1.7.0_45/1.8
   *
   * @param nMaxGeneralEntitySizeLimit
   *        A positive integer. Values &ge; 0 are treated as no limit.
   */
  public static void setXMLMaxGeneralEntitySizeLimit (final int nMaxGeneralEntitySizeLimit)
  {
    setPropertyValue (SYSTEM_PROPERTY_JDX_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT,
                      Integer.toString (nMaxGeneralEntitySizeLimit));
  }

  public static int getXMLMaxGeneralEntitySizeLimit ()
  {
    // Default value:
    // JDK 1.7.0_45: 0
    final String sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_JDX_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT);
    if (sPropertyValue == null)
      return 0;
    return Integer.parseInt (sPropertyValue);
  }

  /**
   * Limit the maximum size of any parameter entities, including the result of
   * nesting multiple parameter entities. It is recommended that users set the
   * limit to the smallest possible number so that malformed xml files can be
   * caught quickly.<br>
   * This is available since JDK 1.7.0_45/1.8
   *
   * @param nMaxParameterEntitySizeLimit
   *        A positive integer. Values &ge; 0 are treated as no limit.
   */
  public static void setXMLMaxParameterEntitySizeLimit (final int nMaxParameterEntitySizeLimit)
  {
    setPropertyValue (SYSTEM_PROPERTY_JDX_XML_MAX_PARAMETER_ENTITY_SIZE_LIMIT,
                      Integer.toString (nMaxParameterEntitySizeLimit));
  }

  public static int getXMLMaxParameterEntitySizeLimit ()
  {
    // Default value:
    // JDK 1.7.0_45: 0
    final String sPropertyValue = getPropertyValueOrNull (SYSTEM_PROPERTY_JDX_XML_MAX_PARAMETER_ENTITY_SIZE_LIMIT);
    if (sPropertyValue == null)
      return 0;
    return Integer.parseInt (sPropertyValue);
  }
}
