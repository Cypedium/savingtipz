<script setup>
import { ref } from 'vue'
import { fetchAllMessages, createMessage, deleteMessage } from '../services/SavingTipzService'
import { Filter } from 'bad-words' // Use named import

defineProps({
  msg: String,
})

const count = ref(0);
const messages = ref([]);
const errorMessage = ref('');
const userInput = ref('');
const savedMessageSuccess = ref('');

// Profanity filter instance
const filter = new Filter()

// Custom validation rule for FormKit
const validateCleanInput = ({ value }) => {
  if (filter.isProfane(value)) return 'Please avoid inappropriate language.'
  // if (/\d/.test(value)) return 'Numbers are not allowed.'
  return true
}

// Fetch messages
const showAllMessages = async () => {
  try {
    messages.value = await fetchAllMessages()
  } catch (error) {
    errorMessage.value = `Error: ${error.response.status}`
  }
}

// Send message
const sendMessage = async () => {
  if (validateCleanInput({ value: userInput.value }) !== true) {
    errorMessage.value = validateCleanInput({ value: userInput.value });
    showAllMessages();
    return
  }
  else {
    try {
      await createMessage(userInput.value);
      userInput.value = '';
      errorMessage.value = '';
      savedMessageSuccess.value = 'Your Saving Tipz has been added successfully!';
      showAllMessages();
    } catch (error) {
      errorMessage.value = `Error: ${error.response.status}`
    }
  }
}
//todo: implement delete functionality with backend
/* const deleteCardMessage = async (message) => {
  console.log("Deleting message with message:", message);
  try {
    await deleteMessage(message);
    showAllMessages();
    savedMessageSuccess.value = 'The Saving Tipz has been deleted successfully!';
  } catch (error) {
    errorMessage.value = `Error: ${error.response.status}`
  }
}  */


</script>

<template>
  <br />

  <h1 :title="msg">{{ msg }}</h1>

  <div class="card">
    <button type="button" @click="showAllMessages">Click here to fetch last 10 rows.</button>
    <div v-if="messages.length" class="message-list">
      <div v-for="(message, index) in messages.slice((messages.length - 10), (messages.length))" :key="index"
        class="message-item">
        {{ message.message }} 
        <!-- <button type="button" @click="deleteCardMessage(message.message)">Delete</button> -->
      </div>
    </div>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>

  <div class="card formkit-wrapper">
    <label for="Add Saving Tipz">Add your SavingTipz here:</label>
    <FormKit id="Add Saving Tipz" type="text" v-model="userInput" placeholder="Write your savingTipz here..."
      :validation="[['required'], [validateCleanInput]]"
      :validation-messages="{ validateCleanInput: 'No bad words or numbers allowed.' }" :classes="{
        inner: 'formkit-inner',
        outer: 'formkit-outer',
        message: 'formkit-message'
      }" />

    <button type="button" @click="sendMessage">Add Saving Tipz</button>
    <p v-if="(savedMessageSuccess !== '')" aria-live="polite" class="success-message">{{ savedMessageSuccess }}</p>

  </div>
</template>

<style scoped>

/* General styles */
h1 {
  color: #333;
  text-align: center;
}

button {
  padding: 0.6rem 1.2rem;
  font-size: 0.9rem;
  background-color: #0078d4;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:hover {
  background-color: #005fa3;
}

/* Specific styles */

.message-list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.message-item {
  padding: 0.5rem;
}

.card {
  margin: 2rem auto;
  padding: 1.5rem;
  max-width: 700px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* FormKit styles */

.formkit-wrapper {
  display: flex;
  flex-direction: column;
  text-align: center;
  gap: 1rem;
  width: 100%;
}

.formkit-outer {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.formkit-inner {
  width: 100%;
  max-width: 500px;
}

/*Error message styling*/

.error-message {
  color: red;
  margin-top: 1rem;
}

.error-message {
  color: red;
}
</style>
