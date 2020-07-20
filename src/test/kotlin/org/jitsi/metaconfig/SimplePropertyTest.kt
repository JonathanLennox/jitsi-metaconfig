/*
 * Copyright @ 2018 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jitsi.metaconfig

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class SimplePropertyTest : ShouldSpec({
    val configSrc = MapConfigSource("test")
    context("a class with a simple property") {
        val obj = object {
            val enabled: Boolean by config("server.enabled".from(configSrc))
        }
        context("when the property is missing") {
            shouldThrow<ConfigException.UnableToRetrieve.NotFound> {
                obj.enabled
            }
        }
        context("when the property is present") {
            configSrc["server.enabled"] = true
            should("read the value correctly") {
                obj.enabled shouldBe true
            }
        }
        context("when the value is the wrong type") {
            configSrc["server.enabled"] = 42
            should("fail to parse") {
                shouldThrow<ConfigException.UnableToRetrieve.WrongType> {
                    obj.enabled
                }
            }
        }
    }
})


