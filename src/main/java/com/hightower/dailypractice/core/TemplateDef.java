package com.hightower.dailypractice.core;

import java.util.Map;

public record TemplateDef(String name,
                          Map<String, Object> args) {
}
