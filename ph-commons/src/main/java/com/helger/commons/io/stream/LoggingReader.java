/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
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
package com.helger.commons.io.stream;

import java.io.IOException;
import java.io.Reader;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.string.ToStringGenerator;

/**
 * A wrapper around an {@link Reader} that logs read and skip actions.
 *
 * @author Philip Helger
 * @since 9.3.8
 */
public class LoggingReader extends WrappedReader
{
  private static final Logger LOGGER = LoggerFactory.getLogger (LoggingReader.class);

  private long m_nPosition = 0;

  /**
   * @param aSourceReader
   *        The reader that should be logged. May not be <code>null</code>.
   */
  public LoggingReader (@Nonnull final Reader aSourceReader)
  {
    super (aSourceReader);
  }

  /**
   * @return The current read position. Always &ge; 0.
   */
  public final long getPosition ()
  {
    return m_nPosition;
  }

  @OverrideOnDemand
  protected void onRead (final int nBytesRead, final long nNewPosition)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("Read " + nBytesRead + " byte(s); now at " + nNewPosition);
  }

  @OverrideOnDemand
  protected void onSkip (final long nBytesSkipped, final long nNewPosition)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("Skipped " + nBytesSkipped + " byte(s); now at " + nNewPosition);
  }

  @OverrideOnDemand
  protected void onMark (final int nReadLimit, final long nCurrentPosition)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("Marked at " + nCurrentPosition + " with read-limit of " + nReadLimit);
  }

  @OverrideOnDemand
  protected void onReset (final long nCurrentPosition)
  {
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("Reset at " + nCurrentPosition);
  }

  @Override
  public final int read () throws IOException
  {
    final int ret = super.read ();
    if (ret != -1)
    {
      m_nPosition++;
      onRead (1, m_nPosition);
    }
    return ret;
  }

  @Override
  public final int read (final char [] aBuf, final int nOffset, final int nLength) throws IOException
  {
    final int ret = super.read (aBuf, nOffset, nLength);
    if (ret != -1)
    {
      m_nPosition += ret;
      onRead (ret, m_nPosition);
    }
    return ret;
  }

  @Override
  public final long skip (@Nonnegative final long n) throws IOException
  {
    final long nSkipped = super.skip (n);
    if (nSkipped > 0)
    {
      m_nPosition += nSkipped;
      onSkip (nSkipped, m_nPosition);
    }
    return nSkipped;
  }

  @Override
  public final void mark (@Nonnegative final int nReadlimit) throws IOException
  {
    super.mark (nReadlimit);
    onMark (nReadlimit, m_nPosition);
  }

  @Override
  public final void reset () throws IOException
  {
    super.reset ();
    onReset (m_nPosition);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("position", m_nPosition).getToString ();
  }
}
