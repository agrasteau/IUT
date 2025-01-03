import axios from 'axios';

const url = 'https://fakestoreapi.com/users';

const response = await axios.get(url);

const users = response.data;

for (const user of users) {
  console.log(user.name);
  console.log(user.email);
}