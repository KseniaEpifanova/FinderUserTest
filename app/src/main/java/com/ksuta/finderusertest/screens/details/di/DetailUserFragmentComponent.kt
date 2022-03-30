package com.ksuta.finderusertest.screens.details.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.ksuta.finderusertest.di.AppComponent
import com.ksuta.finderusertest.di.FragmentScope
import com.ksuta.finderusertest.di.ViewModelProviderModule
import com.ksuta.finderusertest.di.KeyViewModel
import com.ksuta.finderusertest.network.UsersApi
import com.ksuta.finderusertest.network.appComponent
import com.ksuta.finderusertest.screens.details.DetailRepository
import com.ksuta.finderusertest.screens.details.DetailUserFragment
import com.ksuta.finderusertest.screens.details.DetailUserViewModel
import com.ksuta.finderusertest.screens.details.IDetailRepository
import com.ksuta.finderusertest.screens.search.UserModel
import dagger.*
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [DetailUserFragmentModule::class, ViewModelProviderModule::class]
)
interface DetailUserFragmentComponent {
    fun inject(fragment: DetailUserFragment)

    companion object {
        fun init(activity: AppCompatActivity, model: UserModel?): DetailUserFragmentComponent =
            DaggerDetailUserFragmentComponent.factory().newInstance(activity, model, activity.appComponent)
    }

    @Component.Factory
    interface Factory {
        fun newInstance(
            @BindsInstance activity: AppCompatActivity,
            @BindsInstance model: UserModel?,
            appComponent: AppComponent
        ): DetailUserFragmentComponent
    }
}

@Module
abstract class DetailUserFragmentModule {

    @Binds
    @IntoMap
    @KeyViewModel(DetailUserViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: DetailUserViewModel): ViewModel

    @Binds
    abstract fun bindRepo(repo: DetailRepository): IDetailRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideApi(retrofit: Retrofit): UsersApi =
            retrofit.create(UsersApi::class.java)
    }
}
