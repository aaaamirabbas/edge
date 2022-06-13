package io.github.aaaamirabbas.edge.base.component

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseSheetFragment<VB : ViewBinding> :
    BottomSheetDialogFragment(), BaseFragmentView {

    var binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    lateinit var activityContext: AppCompatActivity
    private var isExistInBackStack = false

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        isExistInBackStack = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val contextThemeWrapper = ContextThemeWrapper(requireContext(), theme)
        binding = bindingInflater.invoke(
            inflater.cloneInContext(contextThemeWrapper), container, false
        )

        return requireNotNull(binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewHandler(view, savedInstanceState)
        onLifeCycleHandler()

        isExistInBackStack = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context as BaseActivity<*>
    }

    fun isExistInBackStack(): Boolean {
        return isExistInBackStack
    }

    fun execWhenNotExistInStack(action: () -> Unit) {
        if (!isExistInBackStack) {
            action()
        }
    }
}