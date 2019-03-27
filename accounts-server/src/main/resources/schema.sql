CREATE TABLE accounts
(
  id      int     NOT NULL,
  amount  bigint  NOT NULL
);

CREATE UNIQUE INDEX accounts_pk ON accounts (id);
ALTER TABLE accounts
  ADD CONSTRAINT accounts_pk PRIMARY KEY USING INDEX accounts_pk;