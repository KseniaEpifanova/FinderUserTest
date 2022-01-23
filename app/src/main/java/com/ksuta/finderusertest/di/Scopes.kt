package com.ksuta.finderusertest.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
annotation class ActivityScope

@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class FragmentScope