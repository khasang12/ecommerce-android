package com.example.ecommerce_app.viewmodel

import android.app.Application
import androidx.fragment.app.viewModels
import com.example.ecommerce_app.MainCoroutineRule
import com.example.ecommerce_app.data.User
import com.example.ecommerce_app.fragments.settings.UserAccountFragment
import com.example.ecommerce_app.utils.Resource
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyCollection
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserAccountVMTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockFirestore: FirebaseFirestore

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockStorageReference: StorageReference

    @Mock
    private lateinit var mockApplication: Application

    private lateinit var viewModel: UserAccountViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockStatic(FirebaseAuth::class.java)

        // Mock Firestore behavior
        val mockDocumentSnapshot: DocumentSnapshot = mock(DocumentSnapshot::class.java)
        val mockUser = User("a","b","a@g.cm")

        // Mock the task returned by get()
        val mockSuccessTask: Task<DocumentSnapshot> = Tasks.forResult(mockDocumentSnapshot)
        `when`(mockAuth.uid).thenReturn("abc")
        `when`(mockFirestore.collection("user")).thenReturn(mock(CollectionReference::class.java))
        `when`(mockFirestore.collection("user").document(any())).thenReturn(mock(DocumentReference::class.java))
        `when`(mockFirestore.collection("user").document(any()).get()).thenReturn(mockSuccessTask)

        lenient().`when`(mockDocumentSnapshot.toObject(User::class.java)).thenReturn(mockUser)

        viewModel = UserAccountViewModel(mockFirestore,mockAuth,mockStorageReference,mockApplication)
        reset(mockFirestore)
    }

    @Test
    fun `getUser emits Resource Loading and Success on success`() = mainCoroutineRule.runBlockingTest {

        // Mock Firestore behavior
        val mockDocumentSnapshot: DocumentSnapshot = mock(DocumentSnapshot::class.java)
        val mockUser = User("a","b","a@g.cm")
        val mockSuccessTask: Task<DocumentSnapshot> = Tasks.forResult(mockDocumentSnapshot)
        `when`(mockAuth.uid).thenReturn("abc")
        `when`(mockFirestore.collection("user")).thenReturn(mock(CollectionReference::class.java))
        `when`(mockFirestore.collection("user").document(any())).thenReturn(mock(DocumentReference::class.java))
        `when`(mockFirestore.collection("user").document(any()).get()).thenReturn(mockSuccessTask)

        lenient().`when`(mockDocumentSnapshot.toObject(User::class.java)).thenReturn(mockUser)

        // Call the function to test
        viewModel.getUser()

        // Advance the time to simulate the asynchronous operation completion
        verify(mockFirestore, atLeastOnce()).collection("user")
        verify(mockFirestore.collection("user"), atLeastOnce()).document(any())
        verify(mockFirestore.collection("user").document(any()), atLeastOnce()).get()
        assertTrue(viewModel.user.value is Resource.Loading)
    }

}
