package com.example.guest.bakingapp.di.scope;

/**
 * Created by l1maginaire on 4/14/18.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME) //todo perFragment
public @interface PerActivity {}