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

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;

public class CommonsTreeMap <KEYTYPE, VALUETYPE> extends TreeMap <KEYTYPE, VALUETYPE>
                            implements ICommonsMap <KEYTYPE, VALUETYPE>
{
  public CommonsTreeMap ()
  {}

  public CommonsTreeMap (@Nullable final Comparator <? super KEYTYPE> aComparator)
  {
    super (aComparator);
  }

  public CommonsTreeMap (@Nonnull final Map <? extends KEYTYPE, ? extends VALUETYPE> aMap)
  {
    super (aMap);
  }

  @Nonnull
  @ReturnsMutableCopy
  public CommonsTreeMap <KEYTYPE, VALUETYPE> getClone ()
  {
    final CommonsTreeMap <KEYTYPE, VALUETYPE> ret = new CommonsTreeMap <> (comparator ());
    ret.putAll (this);
    return ret;
  }
}
