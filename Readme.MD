# Scala Crice Decoders

Examples from https://edwardgunawan880.medium.com/6-quick-tips-to-parse-json-with-circe-9bbe51ce5778

The DecodingArraysOfObjectsOfArraysApp is using Scala Cats which is provides advanced means of functional programming.
Go to https://www.baeldung.com/scala/cats-intro for an introduction to cats.

Go to https://typelevel.org/cats/typeclasses/traverse.html for more information about Cats Traverse.

Traverse of the DecodingArraysOfObjectsOfArrays has following type:

```
def traverse[G[_], A, B](fa: F[A])(f: A => G[B])(implicit evidence$1: Applicative[G]): G[F[B]]
```

It takes an Applicative G[_] which is applied to F[A] and turns A into G[B] resulting in G[F[B]].