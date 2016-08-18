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
package com.helger.commons.callback;

/**
 * This is the same as the {@link java.util.concurrent.Callable} interface but
 * following our naming conventions.
 *
 * @author Philip Helger
 * @param <DATATYPE>
 *        The return type of the call.
 * @param <EXTYPE>
 *        Exception type to be thrown
 * @deprecated Use {@link com.helger.commons.function.IThrowingSupplier}
 *             instead.
 */
@Deprecated
@FunctionalInterface
public interface IThrowingCallable <DATATYPE, EXTYPE extends Exception>
{
  DATATYPE call () throws EXTYPE;
}
