/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.ratpack.server

import io.opentelemetry.instrumentation.ratpack.RatpackTracing
import io.opentelemetry.instrumentation.test.LibraryTestTrait
import ratpack.server.RatpackServerSpec

class RatpackHttpServerTest extends AbstractRatpackHttpServerTest implements LibraryTestTrait {
  @Override
  void configure(RatpackServerSpec serverSpec) {
    RatpackTracing tracing = RatpackTracing.builder(openTelemetry)
      .captureHttpHeaders(capturedHttpHeadersForTesting())
      .build()
    serverSpec.registryOf {
      tracing.configureServerRegistry(it)
    }
  }

  @Override
  boolean hasHandlerSpan(ServerEndpoint endpoint) {
    false
  }
}