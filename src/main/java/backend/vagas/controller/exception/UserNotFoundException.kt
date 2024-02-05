package backend.vagas.controller.exception

class UserNotFoundException : RuntimeException("Username or email already exists")
