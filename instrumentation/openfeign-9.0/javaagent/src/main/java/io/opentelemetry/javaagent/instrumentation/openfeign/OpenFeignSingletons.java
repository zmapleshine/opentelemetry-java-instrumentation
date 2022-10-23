/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.openfeign;

import feign.Response;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import io.opentelemetry.instrumentation.api.instrumenter.http.HttpClientAttributesExtractor;
import io.opentelemetry.instrumentation.api.instrumenter.net.NetClientAttributesExtractor;
import io.opentelemetry.instrumentation.api.instrumenter.rpc.RpcClientAttributesExtractor;

public class OpenFeignSingletons {

  private OpenFeignSingletons() {}

  private static final String INSTRUMENTATION_NAME = "io.opentelemetry.openfeign-9.0";
  private static final Instrumenter<ExecuteAndDecodeRequest, Response> INSTRUMENTER;

  static {
    INSTRUMENTER =
        Instrumenter.<ExecuteAndDecodeRequest, Response>builder(
                GlobalOpenTelemetry.get(), INSTRUMENTATION_NAME, new OpenFeignSpanNameExtractor())
            .addAttributesExtractor(
                RpcClientAttributesExtractor.create(OpenFeignRpcAttributesGetter.INSTANCE))
            .addAttributesExtractor(
                HttpClientAttributesExtractor.create(OpenFeignHttpClientAttributesGetter.INSTANCE))
            .addAttributesExtractor(
                NetClientAttributesExtractor.create(OpenFeignNetClientAttributesGetter.INSTANCE))
            .addContextCustomizer(
                (context, request, startAttributes) -> OpenFeignResponseHolder.init(context))
            .buildClientInstrumenter(OpenFeignTextMapSetter.INSTANCE);
  }

  public static Instrumenter<ExecuteAndDecodeRequest, Response> instrumenter() {
    return INSTRUMENTER;
  }
}