package com.example.todoapp.DI

import com.example.todoapp.fragments.EditFragment
import com.example.todoapp.fragments.MainFragment
import com.example.todoapp.fragments.NewTaskFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [FragmentsModule::class, AppModule::class])
interface MainComponentFragments {

    fun inject(instance: MainFragment)

    @Component.Builder
    interface Builder {

        fun build(): MainComponentFragments

        @BindsInstance
        fun componentFragment(componentFragment: ComponentFragments): Builder

    }

}

@Component(modules = [FragmentsModule::class, AppModule::class])
interface EditComponentFragments {

    fun inject(instance: EditFragment)

    @Component.Builder
    interface Builder {

        fun build(): EditComponentFragments

        @BindsInstance
        fun componentFragment(componentFragment: ComponentFragments): Builder

    }

}

@Component(modules = [FragmentsModule::class, AppModule::class])
interface NewTaskComponentFragments {

    fun inject(instance: NewTaskFragment)

    @Component.Builder
    interface Builder {

        fun build(): NewTaskComponentFragments

        @BindsInstance
        fun componentFragment(componentFragment: ComponentFragments): Builder

    }

}
