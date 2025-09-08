<script setup>
import { ref } from 'vue'
import { fetchAllMessages, createMessage } from '../service/HelloWorldService'
import { Filter } from 'bad-words' // Use named import

defineProps({
  msg: String,
})

const count = ref(0)
const messages = ref([])
const errorMessage = ref('')
const userInput = ref('')

// Profanity filter instance
const filter = new Filter()

// Custom validation rule for FormKit
const validateCleanInput = ({ value }) => {
  if (filter.isProfane(value)) return 'Please avoid inappropriate language.'
  if (/\d/.test(value)) return 'Numbers are not allowed.'
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
      userInput.value = ' '
      showAllMessages();
    } catch (error) {
      errorMessage.value = `Error: ${error.response.status}`
    }
  }
}


</script>

<template>
  <br />
  <h1>{{ msg }}</h1>

  <div class="card">
    <button type="button" @click="showAllMessages">Click here to fetch last 10 rows.</button>
    <div v-if="messages.length" class="message-list">
      <div v-for="(message, index) in messages.slice((messages.length - 10), (messages.length))" :key="index" class="message-item">
        {{ message.message }}
      </div>
    </div>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>

  <div class="card formkit-wrapper">
    <FormKit type="text" v-model="userInput" placeholder="Write your savingTipz here..."
      :validation="[['required'], [validateCleanInput]]" :validation-messages="{
        validateCleanInput: 'No bad words or numbers allowed.'
      }" :classes="{
        input: 'formkit-input',
        outer: 'formkit-outer',
        message: 'formkit-message'
      }" />
    <button type="button" @click="sendMessage">Add to Database</button>
  </div>
</template>

<style scoped>

h1 {
  color: #333;
  text-align: center;
}

button {
  padding: 0.6rem 1.2rem;
  font-size: 1rem;
  background-color: #0078d4;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:hover {
  background-color: #005fa3;
}

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

.formkit-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  width: 100%;
}

.formkit-input {
  width: 200px;
  padding: 0.25rem;
  font-size: 4rem;
  border: 1px solid #a59c9c;
  border-radius: 6px;
}



.error-message {
  color: red;
  margin-top: 1rem;
}

.error-message {
  color: red;
}
</style>
