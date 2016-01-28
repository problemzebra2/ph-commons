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
package com.helger.commons.compare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;

/**
 * Test class for {@link IntComparator}
 *
 * @author Philip Helger
 */
public final class AbstractIntComparatorFuncTest
{
  @NotThreadSafe
  private static final class MockComparator extends IntComparator <Integer>
  {
    MockComparator ()
    {
      super (aObject -> aObject.intValue ());
    }
  }

  @Test
  public void testAll ()
  {
    final Integer [] x = new Integer [] { Integer.valueOf (3),
                                          Integer.valueOf (3),
                                          Integer.valueOf (-56),
                                          Integer.valueOf (1) };

    // default: sort ascending
    List <Integer> l = CollectionHelper.getSorted (x, new MockComparator ());
    assertNotNull (l);
    assertEquals (-56, l.get (0).intValue ());
    assertEquals (1, l.get (1).intValue ());
    assertEquals (3, l.get (2).intValue ());
    assertEquals (3, l.get (3).intValue ());

    // Explicitly sort ascending
    l = CollectionHelper.getSorted (x, new MockComparator ());
    assertNotNull (l);
    assertEquals (-56, l.get (0).intValue ());
    assertEquals (1, l.get (1).intValue ());
    assertEquals (3, l.get (2).intValue ());
    assertEquals (3, l.get (3).intValue ());

    // Explicitly sort descending
    l = CollectionHelper.getSorted (x, new MockComparator ().reversed ());
    assertNotNull (l);
    assertEquals (3, l.get (0).intValue ());
    assertEquals (3, l.get (1).intValue ());
    assertEquals (1, l.get (2).intValue ());
    assertEquals (-56, l.get (3).intValue ());

    // change dynamically
    final AbstractComparator <Integer> c = new MockComparator ();
    l = CollectionHelper.getSorted (x, c);
    assertEquals (-56, l.get (0).intValue ());
    assertEquals (1, l.get (1).intValue ());
    assertEquals (3, l.get (2).intValue ());
    assertEquals (3, l.get (3).intValue ());

    // change to descending
    l = CollectionHelper.getSorted (x, c.reversed ());
    assertEquals (3, l.get (0).intValue ());
    assertEquals (3, l.get (1).intValue ());
    assertEquals (1, l.get (2).intValue ());
    assertEquals (-56, l.get (3).intValue ());
  }
}
