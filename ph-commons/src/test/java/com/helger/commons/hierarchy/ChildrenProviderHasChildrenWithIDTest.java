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
package com.helger.commons.hierarchy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link ChildrenProviderHasChildrenWithID}.
 *
 * @author Philip Helger
 */
public final class ChildrenProviderHasChildrenWithIDTest
{
  @Test
  public void testAll ()
  {
    final ChildrenProviderHasChildrenWithID <String, MockHasSortedChildren> cr = new ChildrenProviderHasChildrenWithID <> ();
    assertFalse (cr.hasChildren (null));
    assertEquals (0, cr.getChildCount (null));
    assertNull (cr.getAllChildren (null));
    final MockHasSortedChildren hca = new MockHasSortedChildren ("a");
    final MockHasSortedChildren hcb = new MockHasSortedChildren ("b");
    final MockHasSortedChildren hc1 = new MockHasSortedChildren ("1", hcb, hca);
    assertTrue (cr.hasChildren (hc1));
    assertFalse (cr.hasChildren (hca));
    assertEquals (2, cr.getChildCount (hc1));
    assertEquals (0, cr.getChildCount (hca));
    assertNotNull (cr.getAllChildren (hc1));
    assertNotNull (cr.getAllChildren (hca));
    assertSame (hca, cr.getChildWithID (hc1, "a"));
    assertNull (cr.getChildWithID (hc1, "anyid"));
  }
}
