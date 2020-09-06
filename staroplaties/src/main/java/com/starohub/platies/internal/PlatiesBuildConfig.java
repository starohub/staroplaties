/* SH
 *
 * Modified by Staro Hub @ 2020 [ https://github.com/starohub ]
 *
 * Ref: https://github.com/google/gson/blob/gson-parent-2.8.5/gson/src/main/java/com/google/gson/internal/%24Gson%24Types.java
 *
 * SH
 */

/*
 * Copyright (C) 2018 The Platies authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.starohub.platies.internal;

/**
 * Build configuration for Platies. This file is automatically populated by
 * templating-maven-plugin and .java/.class files are generated for use in Platies.
 *
 * @author Inderjeet Singh
 */
public final class PlatiesBuildConfig {
  // Based on https://stackoverflow.com/questions/2469922/generate-a-version-java-file-in-maven

  /** This field is automatically populated by Maven when a build is triggered */
  public static final String VERSION = "2.8.5";

  private PlatiesBuildConfig() { }
}
