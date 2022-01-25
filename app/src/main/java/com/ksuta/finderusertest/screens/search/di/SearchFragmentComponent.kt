package com.ksuta.finderusertest.screens.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.ksuta.finderusertest.di.AppComponent
import com.ksuta.finderusertest.di.FragmentScope
import com.ksuta.finderusertest.di.ViewModelProviderModule
import com.ksuta.finderusertest.di.KeyViewModel
import com.ksuta.finderusertest.network.ISearchRepository
import com.ksuta.finderusertest.network.SearchRepository
import com.ksuta.finderusertest.network.UsersApi
import com.ksuta.finderusertest.network.appComponent
import com.ksuta.finderusertest.screens.search.SearchFragment
import com.ksuta.finderusertest.screens.search.SearchViewModel
import dagger.*
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [SearchFragmentModule::class, ViewModelProviderModule::class]
)
interface SearchFragmentComponent {
    fun inject(fragment: SearchFragment)

    companion object {
        fun init(activity: AppCompatActivity): SearchFragmentComponent =
            DaggerSearchFragmentComponent.factory().newInstance(activity, activity.appComponent)
    }

    @Component.Factory
    interface Factory {
        fun newInstance(
            @BindsInstance activity: AppCompatActivity,
            appComponent: AppComponent
        ): SearchFragmentComponent
    }
}

@Module
abstract class SearchFragmentModule {

    @Binds
    @IntoMap
    @KeyViewModel(SearchViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    abstract fun bindRepo(repo: SearchRepository): ISearchRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideApi(retrofit: Retrofit): UsersApi =
            retrofit.create(UsersApi::class.java)
    }
}
