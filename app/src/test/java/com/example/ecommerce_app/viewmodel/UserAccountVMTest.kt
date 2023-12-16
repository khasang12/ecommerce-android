package com.example.ecommerce_app.viewmodel

import android.app.Application
import com.example.ecommerce_app.MainCoroutineRule
import com.example.ecommerce_app.data.User
import com.example.ecommerce_app.utils.Resource
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
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

    @InjectMocks
    private lateinit var viewModel: UserAccountViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = UserAccountViewModel(mockFirestore, mockAuth, mockStorageReference, mock(
            Application::class.java))
    }

    @Test
    fun `getUser emits Resource Loading and Success on success`() = mainCoroutineRule.runBlockingTest {
        // Mock FirebaseAuth behavior
        `when`(mockAuth.uid).thenReturn("mockUid")

        // Mock Firestore behavior
        val mockDocumentSnapshot: DocumentSnapshot = mock(DocumentSnapshot::class.java)
        val mockUser = mock(User::class.java)

        // Mock the task returned by get()
        val mockSuccessTask: Task<DocumentSnapshot>? = null
        `when`(mockFirestore.collection("user").document("mockUid").get()).thenReturn(mockSuccessTask)
        `when`(mockDocumentSnapshot.toObject(User::class.java)).thenReturn(mockUser)

        // Call the function to test
        viewModel.getUser()

        // Verify that Resource Success is emitted after the asynchronous operation
        assertEquals(Resource.Success(mockUser), viewModel.user.value)
    }

}
