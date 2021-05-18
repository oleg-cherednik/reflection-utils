/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ru.olegcherednik.utils.reflection.data;

/**
 * @author Oleg Cherednik
 * @since 28.02.2021
 */
@SuppressWarnings("unused")
public class TypeData {

    private int valInt;
    private Integer valInteger;

    private boolean valBoolean;
    private Boolean valBooleanWrapper;

    private byte valByte;
    private Byte valByteWrapper;

    private char valChar;
    private Character valCharacter;

    private double valDouble;
    private Double valDoubleWrapper;

    private long valLong;
    private Long valLongWrapper;

    private short valShort;
    private Short valShortWrapper;

    private float valFloat;
    private Float valFloatWrapper;

    public int getValInt() {
        return valInt;
    }

    public Integer getValInteger() {
        return valInteger;
    }

    public boolean isValBoolean() {
        return valBoolean;
    }

    public Boolean getValBooleanWrapper() {
        return valBooleanWrapper;
    }

    public byte getValByte() {
        return valByte;
    }

    public Byte getValByteWrapper() {
        return valByteWrapper;
    }

    public char getValChar() {
        return valChar;
    }

    public Character getValCharacter() {
        return valCharacter;
    }

    public double getValDouble() {
        return valDouble;
    }

    public Double getValDoubleWrapper() {
        return valDoubleWrapper;
    }

    public long getValLong() {
        return valLong;
    }

    public Long getValLongWrapper() {
        return valLongWrapper;
    }

    public short getValShort() {
        return valShort;
    }

    public Short getValShortWrapper() {
        return valShortWrapper;
    }

    public float getValFloat() {
        return valFloat;
    }

    public Float getValFloatWrapper() {
        return valFloatWrapper;
    }

}
