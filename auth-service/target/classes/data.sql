INSERT INTO usuarios (username, password, roles)
SELECT 'admin', '$2a$10$TU_HASH_BCRYPT_AQUI', 'ADMIN'
    WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE username = 'admin'
);