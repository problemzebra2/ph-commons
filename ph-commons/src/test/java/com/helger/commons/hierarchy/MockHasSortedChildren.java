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
package com.helger.commons.hierarchy;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.id.IHasID;

public final class MockHasSortedChildren implements IHasChildrenSorted <MockHasSortedChildren>, IHasID <String>
{
  private final String m_sID;
  private final ICommonsList <MockHasSortedChildren> m_aList;

  public MockHasSortedChildren (@Nonnull final String sID, @Nullable final MockHasSortedChildren... aList)
  {
    m_sID = sID;
    m_aList = CollectionHelper.getSorted (aList, IHasID.getComparatorID ());
  }

  public String getID ()
  {
    return m_sID;
  }

  public boolean hasChildren ()
  {
    return m_aList.isNotEmpty ();
  }

  @Nonnegative
  public int getChildCount ()
  {
    return m_aList.size ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <? extends MockHasSortedChildren> getAllChildren ()
  {
    return m_aList.getClone ();
  }

  public void forAllChildren (@Nonnull final Consumer <? super MockHasSortedChildren> aConsumer)
  {
    m_aList.forEach (aConsumer);
  }

  public void forAllChildren (@Nonnull final Predicate <? super MockHasSortedChildren> aFilter,
                              @Nonnull final Consumer <? super MockHasSortedChildren> aConsumer)
  {
    m_aList.findAll (aFilter, aConsumer);
  }

  public <DSTTYPE> void forAllChildrenMapped (@Nonnull final Predicate <? super MockHasSortedChildren> aFilter,
                                              @Nonnull final Function <? super MockHasSortedChildren, ? extends DSTTYPE> aMapper,
                                              @Nonnull final Consumer <? super DSTTYPE> aConsumer)
  {
    m_aList.findAllMapped (aFilter, aMapper, aConsumer);
  }

  @Nullable
  public MockHasSortedChildren getChildAtIndex (final int nIndex)
  {
    return m_aList.get (nIndex);
  }

  @Nullable
  public MockHasSortedChildren getFirstChild ()
  {
    return CollectionHelper.getFirstElement (m_aList);
  }

  @Nullable
  public MockHasSortedChildren getLastChild ()
  {
    return CollectionHelper.getLastElement (m_aList);
  }
}
