package com.example.guest.bakingapp.data.local;

/**
 * Created by l1maginaire on 5/6/18.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalScope {}
