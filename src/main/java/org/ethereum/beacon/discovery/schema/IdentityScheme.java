/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.ethereum.beacon.discovery.schema;

import java.util.HashMap;
import java.util.Map;

/** Discovery protocol identity schemas */
public enum IdentitySchema {
  V4("v4"),
  V5("v5");

  private static final Map<String, IdentitySchema> NAME_MAP = new HashMap<>();

  static {
    for (IdentitySchema schema : IdentitySchema.values()) {
      NAME_MAP.put(schema.name, schema);
    }
  }

  private final String name;

  private IdentitySchema(String name) {
    this.name = name;
  }

  public static IdentitySchema fromString(String name) {
    return NAME_MAP.get(name);
  }

  public String stringName() {
    return name;
  }
}
