package com.sample.telemetrynewrelic.utils;

import com.sample.telemetrynewrelic.core.Istorage;
import com.sample.telemetrynewrelic.core.TraceConstants;
import io.grpc.Context;
import java.util.Objects;

/**
 * The type Extract trace context.
 */
public final class ExtractTraceContext {

  /**
   * The Trace name.
   */
  private final transient String traceName;

  /**
   * The Storage.
   */
  private final transient Istorage storage;

  /**
   * Instantiates a new Extract trace context.
   *
   * @param traceName the trace name
   * @param storage   the storage
   */
  public ExtractTraceContext(final String traceName, final Istorage storage) {
    Objects.requireNonNull(traceName);
    Objects.requireNonNull(storage);

    this.traceName = traceName;
    this.storage = storage;
  }

  /**
   * Extract context.
   *
   * @return the context
   */
  public Context extract() {
    return
        storage.get(traceName + TraceConstants.TRACE_PARENT_DATA).getTraceSpan().withSpan(Context.current());
  }
}
