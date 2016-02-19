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
package com.helger.commons.collection.ext;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link CommonsHashSet}.
 *
 * @author Philip Helger
 */
public final class CommonsHashSetTest
{
  @Test
  public void testBasic ()
  {
    final ICommonsSet <String> aTest = new CommonsHashSet <> ();
    aTest.add ("aaa");
    aTest.add ("bbb");
    aTest.add ("ccc");

    final ICommonsList <String> aSortedKeys = aTest.getSorted (Comparator.naturalOrder ());
    assertEquals ("aaa", aSortedKeys.getAtIndex (0));
    assertEquals ("bbb", aSortedKeys.getAtIndex (1));
    assertEquals ("ccc", aSortedKeys.getAtIndex (2));

    CommonsTestHelper.testDefaultSerialization (aTest);
    CommonsTestHelper.testGetClone (aTest);
  }
}
