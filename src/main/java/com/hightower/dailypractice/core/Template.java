package com.hightower.dailypractice.core;

import java.util.Map;

public record Template(String name,
                       Map<String, Object> args) {
}
