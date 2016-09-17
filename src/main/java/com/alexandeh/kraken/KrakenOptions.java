package com.alexandeh.kraken;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright 2016 Alexander Maxwell
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Alexander Maxwell
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class KrakenOptions {

    private boolean sendCreationMessage;

    public static KrakenOptions getDefaultOptions() {
        return new KrakenOptions().sendCreationMessage(true);
    }

}
