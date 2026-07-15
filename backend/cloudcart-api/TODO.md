# TODO - Add unit tests

- [ ] Inspect remaining relevant production classes (if any) needed for controller/service testing.
- [ ] Add `OrderMapperTest` unit tests.
- [ ] Add `OrderServiceImplTest` unit tests using Mockito.
- [ ] Add `OrderControllerTest` using Spring `MockMvc`:
  - [ ] 201 response + body + `Set-Cookie` header assertion.
  - [ ] Validation failure (400) for invalid request payload.
- [ ] Run `mvn test` and ensure build is green.

