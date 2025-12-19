package com.ontey.inevitable.api.variable.argument.type;

import com.ontey.inevitable.api.variable.argument.VariableArgumentTypeException;

public interface VariableArgumentType<T> {
   T parse(String argument) throws VariableArgumentTypeException;
}
