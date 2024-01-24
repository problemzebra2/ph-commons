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
package com.helger.commons.url;

import java.nio.charset.Charset;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.codec.IDecoder;

/**
 * Decoder for URL parameters
 *
 * @author Philip Helger
 */
public class URLParameterDecoder implements IDecoder <String, String>
{
  private final Charset m_aCharset;

  public URLParameterDecoder (@Nonnull final Charset aCharset)
  {
    m_aCharset = ValueEnforcer.notNull (aCharset, "Charset");
  }

  /**
   * @return The charset passed in the constructor. Never <code>null</code>.
   * @since 9.4.1
   */
  @Nonnull
  public final Charset getCharset ()
  {
    return m_aCharset;
  }

  @Nullable
  public String getDecoded (@Nullable final String sInput)
  {
    return URLHelper.urlDecodeOrNull (sInput, m_aCharset);
  }
}
