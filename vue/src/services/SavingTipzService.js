import axios from 'axios'

export const fetchAllMessages = () => {
  return new Promise((resolve, reject) =>
    axios
      .get('http://localhost:8080/all')
      .then((res) => resolve(res.data))
      .catch((err) => reject(err))
  );
}

export const createMessage = (message) => {
  return axios.post('http://localhost:8080/new', { message }, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
}
  
export const deleteMessage = (id) => {
    return axios.delete(`http://localhost:8080/delete/${id}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
}
