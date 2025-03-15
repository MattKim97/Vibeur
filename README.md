# Vibeur
Share your moments and vibes!

### Vibeur is a app that displays a new way of social interactions! This platform offers a unique blend of visual storytelling with vibe-based music sharing. It allows users to express their emotions and creativity, find inspiration, connections, and comfort within the Vibeur community!

# User Stories

## User

### User Registration
- Users can create an account by providing a username and password.
- Users may optionally upload a profile picture.

### User Login/Logout
- Users can log in using their credentials.
- Users can log out securely.

### Profile Management
- Users can update their profile picture.

## Vibes

### Upload Vibe
- Logged in users can upload an vibe with a title, description, vibe song URL, and vibe.
- Upload date and user ID are automatically recorded.
- If you are not logged in, do not allow creation of vibe

### Vibe Viewing

- Users can browse uploaded vibes, view titles, descriptions, and listen to associated Vibe songs.

- vibes can be filtered or sorted by upload date uploaded, vibe, likes, user's vibes.

- Anyone can view whether logged in or not.

### Vibe Management

- Logged in users can delete or edit their own uploaded vibes (title, description).


## Likes

### Liking

- Logged in users may like an Image which records their user ID, the image ID.

- Logged in users can unlike an image, removing the like entry.

### Like Viewing

- View Like Count

- Anyone can view the total number of likes for each image.


## Comment

### Comment on an Image


- Logged in Users can leave a comment on an image.

- Comments are stored with a timestamp for reference.

- Logged in users can edit and delete their own comments.

### Comment Viewing

- Anyone can view all comments on an image.


# Database Schema
![alt text](images/db%20schema/image.png)


- renamed vibe table to => mood
- renamed image table to -> vibe

### User Table
- user_id integer pk increments unique
- username text
- password text
- user_image_url text null

### Mood Table
- mood_id integer pk increments unique
- mood_name text

### Vibe Table
- vibe_id integer pk increments unique
- title text
- description text
- vibe_url text
- vibe_song text
- date_uploaded datetime
- user_id integer > fk references user.user_id
- mood_id integer > fk references mood.mood_id

### Comment Table
- comment_id integer pk increments unique
- content text
- user_id integer > fk references user.user_id
- vibe_id integer > fk references vibe.vibe_id

### Like Table
like_id integer pk increments unique
vibe_id integer > fk references vibe.vibe_id
user_id integer > fk references user.user_id


# Wireframes

## Not logged in user viewing Landing page
![alt text](images/wireframes/image-2.png)

## Logged in user viewing Landing page
![alt text](images/wireframes/image-1.png)

## Logged out user viewing Vibes
![alt text](images/wireframes/image-4.png)

## Logged in user viewing Vibes
![alt text](images/wireframes/image-5.png)

## Logged in user my vibes page
![alt text](images/wireframes/image-6.png)

## Logged out user Vibe page 
![alt text](images/wireframes/image-8.png)

## Logged in user Vibe page 
![alt text](images/wireframes/image-11.png)

## Logged in user Comment form
![alt text](images/wireframes/image-10.png)

## Logged in user edit Comment form
![alt text](images/wireframes/editcomment.png)

## Logged in user upload a vibe
![alt text](images/wireframes/image-3.png)

## Logged in user edit a vibe
![alt text](images/wireframes/edit.png)

## Login
![alt text](images/wireframes/login.png)

## Signup
![alt text](images/wireframes/signup.png)
