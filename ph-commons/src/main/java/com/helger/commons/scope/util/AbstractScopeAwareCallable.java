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
package com.helger.commons.scope.util;

import java.util.concurrent.Callable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.callback.INonThrowingCallable;
import com.helger.commons.scope.mgr.ScopeManager;
import com.helger.commons.scope.mock.ScopeAwareTestSetup;

/**
 * Abstract implementation of {@link Callable} that handles scopes correctly.
 *
 * @author Philip Helger
 * @param <DATATYPE>
 *        The return type of the function.
 */
@Deprecated
@Immutable
public abstract class AbstractScopeAwareCallable <DATATYPE> extends AbstractScopeAwareAction
                                                 implements INonThrowingCallable <DATATYPE>
{
  public AbstractScopeAwareCallable ()
  {
    this (ScopeManager.getApplicationScope ().getID ());
  }

  public AbstractScopeAwareCallable (@Nonnull @Nonempty final String sApplicationID)
  {
    this (sApplicationID, ScopeAwareTestSetup.MOCK_REQUEST_SCOPE_ID, ScopeAwareTestSetup.MOCK_SESSION_SCOPE_ID);
  }

  public AbstractScopeAwareCallable (@Nonnull @Nonempty final String sApplicationID,
                                     @Nonnull @Nonempty final String sRequestID,
                                     @Nonnull @Nonempty final String sSessionID)
  {
    super (sApplicationID, sRequestID, sSessionID);
  }

  /**
   * Implement your code in here
   *
   * @return The return value of the {@link #call()} method.
   */
  @Nullable
  protected abstract DATATYPE scopedRun ();

  @Nullable
  public final DATATYPE call ()
  {
    ScopeManager.onRequestBegin (m_sApplicationID, m_sRequestID, m_sSessionID);
    try
    {
      final DATATYPE ret = scopedRun ();
      return ret;
    }
    finally
    {
      ScopeManager.onRequestEnd ();
    }
  }
}
