Приложеният файл съдържа колекция от "unit" xml елементи, като всеки unit може да require-ва други unit-и.

Да се напише програма, която по зададено unit id намира unit с даденото id и принтира дървото с неговите транзитивни requirements (requirements на requirements, докато съществуват такива с даденото namespace и name), който имат:

- namespace org.eclipse.equinox.p2.iu или osgi.bundle

- name различно от съответното unit id

 

Пример: По зададен вход "org.eclipse.equinox.p2.director" програмата принтира (коментарите в цвят са за указание, не се включват в output-a):

org.eclipse.equinox.p2.director

--org.eclipse.equinox.common (първо ниво на requirement)

--org.eclipse.core.jobs

--org.eclipse.equinox.p2.metadata

----org.eclipse.equinox.p2.core (второ ниво на requirement)

--org.sat4j.core

--org.sat4j.pb
