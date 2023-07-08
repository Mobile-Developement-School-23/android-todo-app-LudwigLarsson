package com.example.todoapp.DI

import android.app.Activity
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.ItemDao
import com.example.todoapp.model.ItemViewModel
import com.example.todoapp.model.ItemViewModelFactory
import com.example.todoapp.model.ToDoApplication
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class, FragmentsModule::class])
class AppModule {
    @Provides
    fun provideApplication(): ToDoApplication {
        return ToDoApplication.getInstance()
    }
    @Provides
    fun provideItemViewModel(componentFragments: ComponentFragments, itemViewModelFactory: ItemViewModelFactory): ItemViewModel {
        return ViewModelProvider(
            componentFragments.activity as ViewModelStoreOwner,
            itemViewModelFactory
        )[ItemViewModel::class.java]
    }
}
@Module
class DataModule {
    @Provides
    fun provideItemDao(application: ToDoApplication): ItemDao {
        return AppDatabase.getDatabase(application).itemDao()
    }

}
@Module
class FragmentsModule() {
    @Provides
    fun provideActivity(componentFragments: ComponentFragments): Activity {
        return componentFragments.activity
    }

    @Provides
    fun provideRootView(componentFragments: ComponentFragments): View {
        return componentFragments.rootView
    }

    @Provides
    fun provideLifeCycleOwner(componentFragments: ComponentFragments): LifecycleOwner {
        return componentFragments.lifecycleOwner
    }
}