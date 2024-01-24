/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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
package com.helger.commons.io.file;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link ERandomAccessFileMode}.
 *
 * @author Philip Helger
 */
public final class ERandomAccessFileModeTest
{
  @Test
  public void testAll ()
  {
    for (final ERandomAccessFileMode e : ERandomAccessFileMode.values ())
    {
      assertSame (e, ERandomAccessFileMode.valueOf (e.name ()));
      assertTrue (StringHelper.hasText (e.getMode ()));
      assertSame (e, ERandomAccessFileMode.getFromModeOrNull (e.getMode ()));
    }
    assertNull (ERandomAccessFileMode.getFromModeOrNull (null));
    assertNull (ERandomAccessFileMode.getFromModeOrNull (""));
    assertNull (ERandomAccessFileMode.getFromModeOrNull ("bla"));
  }
}
