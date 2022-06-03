/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/ignored")
public interface Resource {
  @Path("ignored")
  String hello(String name);

  @Path("/test")
  interface SubResource extends Cloneable, Resource {
    @Override
    @POST
    @Path("/hello/{name}")
    String hello(@PathParam("name") String name);
  }

  class Test1 implements SubResource {
    @Override
    public String hello(String name) {
      return "Test1 " + name + "!";
    }
  }

  @Path("/test2")
  class Test2 implements SubResource {
    @Override
    public String hello(String name) {
      return "Test2 " + name + "!";
    }
  }

  @Path("/test3")
  class Test3 implements SubResource {
    @Override
    @POST
    @Path("/hi/{name}")
    public String hello(@PathParam("name") String name) {
      return "Test3 " + name + "!";
    }

    @POST
    @Path("/nested")
    public String nested() {
      return hello("nested");
    }
  }
}
